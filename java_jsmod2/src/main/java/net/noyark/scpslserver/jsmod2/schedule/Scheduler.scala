package net.noyark.scpslserver.jsmod2.schedule

import java.util.concurrent.{Callable, Executors}

import net.noyark.scpslserver.jsmod2.Server

/**
  * 中央线程处理器
  * @author magiclu550 #(code) jsmod2
  */

class Scheduler {

  private val service = Executors.newCachedThreadPool

  def execute(c: Callable[_]): Any = try service.submit(c).get


  def execute(runnable: Runnable)= service.execute(runnable)

  def shutdown : Unit = service.shutdown

  def getPool = service

}