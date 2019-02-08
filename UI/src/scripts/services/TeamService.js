define(['footballManager'], function(footballManager) {

  footballManager.service('TeamService', function($http, $q) {
      this.url = './api/team';

      this.getTeams = function () {
        return $http.get(this.url + '/teams');
      };

      this.getTeam = function () {
        return $http.get(this.url + '/team');
      };

      this.chooseTeam = function (teamId) {
        const body = JSON.stringify({teamId: teamId});
        return $http.post(this.url + '/chooseTeam', body);
      }

  });
});