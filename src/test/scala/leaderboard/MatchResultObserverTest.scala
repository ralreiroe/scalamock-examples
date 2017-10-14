package com.example.leaderboard

import java.io.PrintWriter

import com.example.leaderboard.leaderboard._
import org.scalamock.scalatest.MockFactory
import org.scalatest.FlatSpec

class MatchResultObserverTest extends FlatSpec with MockFactory {

  val winner = Player(id = 222, nickname = "boris", country = Countries.Russia)
  val loser = Player(id = 333, nickname = "hans", country = Countries.Germany)

  """For MatchResult(winner = winner.id, loser = loser.id), recordMatchResult""" should "update PlayerLeaderBoard after finished match" in {

    //mocks
    val countryLeaderBoardMock = mock[CountryLeaderboard]
    class MyPrintWriter extends PrintWriter("testfile")   //can only mock classes with default constructor hence creating one
    var writer: PrintWriter = mock[MyPrintWriter]

    //mock expectations
    (countryLeaderBoardMock.addVictoryForCountry _).expects(Countries.Russia)     //<==================================
    (writer.close _).expects().once()

    //stubs
    val userDetailsServiceStub = stub[PlayerDatabase]

    //stub configuration
    (userDetailsServiceStub.getPlayerById _) when loser.id returns loser
    (userDetailsServiceStub.getPlayerById _) when winner.id returns winner

    val matchResultObserver = new MatchResultObserver(userDetailsServiceStub, countryLeaderBoardMock, writer)

    // run system under test
    matchResultObserver.updateCountryLeaderBoardBasedOn(MatchResult(winner = winner.id, loser = loser.id))
  }
}
