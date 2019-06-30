define(['footballManager'], function(footballManager) {

  footballManager.service('FormationService', function($http) {
    this.url = "./api/v1/";

    this.getFormation = function () {
        return $http.get(this.url + 'formation');
    };

    this.fillPositionArrays = function (players, goalKeepers, backPlayers, wingPlayers, frontPlayers) {
        players.forEach(function (player) {
            switch(player.position) {
                case 0:
                  goalKeepers.push(player);
                  break;
                case 1:
                  backPlayers.push(player);
                  break;
                case 2:
                  wingPlayers.push(player);
                  break;
                case 3:
                  frontPlayers.push(player);
            }
        });
    };

    this.saveFormation = function (formation) {
      const body = JSON.stringify({formation: formation});
      return $http.post(this.url + 'formation', body);
    };
  })
});
