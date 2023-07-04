package store.baribari.demo.service

import org.springframework.stereotype.Service

@Service
interface FileService {
    suspend fun geneatePreSingedUrl(
        userEmail: String,
    ): String
}

