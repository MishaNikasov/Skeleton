package com.lampa.skeleton.data.mapper

interface EntityMapper<Entity, DomainModel> {

    fun mapFromEntity(entity: Entity): DomainModel

    fun mapToEntity(domainModel: DomainModel): Entity

    fun mapFromEntityList(entities: List<Entity>): List<DomainModel> {
        return entities.map { e -> mapFromEntity(e) }
    }

}