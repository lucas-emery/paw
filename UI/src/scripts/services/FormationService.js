define(['footballManager', 'services/SettingsService'], function(footballManager) {

  footballManager.service('FormationService', function($http, SettingsService) {
    this.url = SettingsService.getUrl() + 'formation/';

    this.getFormation = function () {
        return $http.get(this.url);
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
      var body = JSON.stringify(formation);
      return $http.post(this.url, body);
    };
  })
});
