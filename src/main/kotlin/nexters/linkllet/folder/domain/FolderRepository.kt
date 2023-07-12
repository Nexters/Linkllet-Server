package nexters.linkllet.folder.domain

import org.springframework.data.jpa.repository.JpaRepository

interface FolderRepository: JpaRepository<Folder, Long> {
}
