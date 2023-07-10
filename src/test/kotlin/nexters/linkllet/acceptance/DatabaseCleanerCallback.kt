package nexters.linkllet.acceptance

import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.test.context.junit.jupiter.SpringExtension

class DatabaseCleanerCallback : BeforeEachCallback {

    override fun beforeEach(context: ExtensionContext) {
        SpringExtension.getApplicationContext(context)
                .getBean(DatabaseCleaner::class.java)
                .execute()
    }
}
