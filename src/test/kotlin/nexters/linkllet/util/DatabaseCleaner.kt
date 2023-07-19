package nexters.linkllet.util

import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.Table
import javax.persistence.metamodel.EntityType

@Component
class DatabaseCleaner(
        @PersistenceContext private val entityManager: EntityManager,
        private val tableNames: MutableList<String> = mutableListOf(),
) : InitializingBean {

    override fun afterPropertiesSet() {
        val tableNames = entityManager.metamodel.entities
                .map { e -> extractTableName(e).lowercase() }
        this.tableNames.addAll(tableNames)
    }

    private fun extractTableName(e: EntityType<*>): String {
        val tableAnnotation = e.javaType.getAnnotation(Table::class.java)
        return tableAnnotation?.name ?: e.name
    }

    @Transactional
    fun execute() {
        entityManager.flush()
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate()

        tableNames.forEach {
            entityManager.createNativeQuery("TRUNCATE TABLE $it").executeUpdate()
            entityManager.createNativeQuery("ALTER TABLE $it AUTO_INCREMENT = 1").executeUpdate()
        }

        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate()
    }
}
