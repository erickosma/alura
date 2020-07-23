package com.listavip.listavip.repository

import com.listavip.listavip.model.Guest
import org.springframework.data.repository.CrudRepository

interface GuestRepository : CrudRepository<Guest?, Long?>