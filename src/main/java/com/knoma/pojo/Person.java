package com.knoma.pojo;

import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.core.annotation.Introspected;

import java.util.UUID;

@Entity
@Introspected
@Serdeable
public record Person(@PartitionKey UUID id, String firstName, String lastName, String email) {
}
