package com.example.newsapp.Domain.Model

enum class ActiveSettingSection {
  LANGUAGE, COUNTRY, DOMAIN, IDLE;
  
  override fun toString(): String {
	return when (this) {
	  LANGUAGE -> "Language"
	  COUNTRY -> "Country"
	  DOMAIN -> "Domain"
	  IDLE -> "Idle"
	}
  }
  
}
