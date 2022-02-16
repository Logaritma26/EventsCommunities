package com.log.eventscommunities.domain.model

data class UserDocument(
    val attending: List<String> = emptyList(),
    val email: String,
    val name: String?,
    val uid: String,
)