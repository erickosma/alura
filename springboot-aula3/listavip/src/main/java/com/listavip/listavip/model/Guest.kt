package com.listavip.listavip.model

import lombok.Getter
import lombok.Setter
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Getter
@Setter
@Entity(name = "convidado")
class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var nome: String? = null
    var email: String? = null
    var telefone: String? = null

    constructor() {}

    constructor(nome: String?, email: String?, telefone: String?) : super() {
        this.nome = nome
        this.email = email
        this.telefone = telefone
    }
}