package com.listavip.listavip.controller

import com.listavip.listavip.model.Guest
import com.listavip.listavip.repository.GuestRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

@Controller
class GuestController {
    @Autowired
    private val repository: GuestRepository? = null

    @RequestMapping("/")
    fun index(): String? {
        return "index"
    }

    @RequestMapping("listaconvidados")
    fun listaConvidados(model: Model): String? {
        val guests: MutableIterable<Guest?> = repository!!.findAll()
        model.addAttribute("convidados", guests)

        return "listaconvidados"
    }

    @RequestMapping(value = ["salvar"], method = [RequestMethod.POST])
    fun salvar(@RequestParam("nome") nome: String?, @RequestParam("email") email: String?,
               @RequestParam("telefone") telefone: String?, model: Model): String? {
        val guest = Guest(nome, email, telefone)
        repository!!.save(guest)
        val guests: MutableIterable<Guest?>? = repository.findAll()
        model.addAttribute("convidados", guests)
        return "listaconvidados"
    }
}