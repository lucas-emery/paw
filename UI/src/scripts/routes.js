define([], function() {
  return {
    defaultRoutePath: '/login',
    routes: {
      '/':{
        templateUrl: "./views/home.html",
        controller: "HomeCtl"
      },
      '/home/:playerId': {
        templateUrl: "./views/home.html",
        controller: "HomeCtl"
      },
      '/chooseTeam': {
        templateUrl: "./views/chooseTeam.html",
        controller: "ChooseTeamCtl"
      },
      '/finance': {
        templateUrl: "./views/finance.html",
        controller: "FinanceCtl"
      },
      '/formation': {
        templateUrl: "./views/formation.html",
        controller: "FormationCtl"
      },
      '/league': {
        templateUrl: "./views/league.html",
        controller: "LeagueCtl"
      },
      '/login': {
        templateUrl: "./views/login.html",
        controller: "LoginCtl"
      },
      '/match': {
        templateUrl: "./views/match.html",
        controller: "MatchCtl"
      },
      '/matchEnd': {
        templateUrl: "./views/matchEnd.html",
        controller: "MatchEndCtl"
      },
      '/stadium': {
        templateUrl: "./views/stadium.html",
        controller: "StadiumCtl"
      },
      '/transfer': {
        templateUrl: "./views/transfer.html",
        controller: "TransferCtl"
      }
    }
  }
});
