package com.stevade.visitationtracker.models;

import com.stevade.visitationtracker.common.Base;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Organization extends Base {
    private String name;
    private String address;
    @OneToMany
    private Set<Member> members;
}
