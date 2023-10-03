package com.knoma.pojo;

import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.ReflectiveAccess;

import java.util.UUID;

@Entity
@Introspected
public record Person(@PartitionKey UUID id, String firstName, String lastName, String email) {
}
