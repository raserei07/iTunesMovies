package com.aargoncillo.component.itunesmovies.data.network.mapper

interface DomainMapper<T, DomainModel> {
  fun mapToDomainModel(model: T): DomainModel
  fun mapFromDomainModel(domainModel: DomainModel): T
}