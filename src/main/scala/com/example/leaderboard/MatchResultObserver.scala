package com.example.leaderboard.leaderboard
import java.io.PrintWriter

import Player.PlayerId

case class MatchResult(winner: PlayerId, loser: PlayerId)

class MatchResultObserver(
  playerDatabase: PlayerDatabase,
  countryLeaderBoard: CountryLeaderboard,
  writer: PrintWriter) {

  def updateCountryLeaderBoardBasedOn(result: MatchResult): Unit = {
    writer.close
    val player = playerDatabase.getPlayerById(result.winner)
    countryLeaderBoard.addVictoryForCountry(player.country)
  }
}

