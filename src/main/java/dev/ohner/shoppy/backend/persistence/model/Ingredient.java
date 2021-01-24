package dev.ohner.shoppy.backend.persistence.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Entity
public class Ingredient implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final long id;
    private final String name;

    @Override
    public String toString() {
        return name;
    }
}
