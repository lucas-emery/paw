define(['footballManager', 'services/SettingsService', 'services/AccountService'], function(footballManager) {

   footballManager.service('MatchService', function($http, SettingsService, AccountService) {
       this.url = SettingsService.getUrl() + 'matches/';

       this.getMatch = function () {
         return AccountService.get(this.url + 'current');
       };

       this.getCurrentMatches = function () {
           return AccountService.get(this.url + 'currents');
       };

       this.getUpcomingMatch = function () {
         return AccountService.get(this.url + 'next');
       };

       this.playMatch = function () {
         return AccountService.get(this.url + 'play');
       };

   })
});
