package com.dirzaaulia.allaboutvalorant.data.response

import com.dirzaaulia.allaboutvalorant.data.model.Agent

data class AgentResponse(
  val status: Int?,
  val data: List<Agent>?,
  val error: String?
)