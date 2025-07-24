package com.moly.edi.domain.repository

import com.moly.edi.domain.model.Conecta

interface ConectaRepository {
    suspend fun getConectaByEmail(email: String): Result<Conecta>
}