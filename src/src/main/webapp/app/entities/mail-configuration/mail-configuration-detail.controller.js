(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Mail_configurationDetailController', Mail_configurationDetailController);

    Mail_configurationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Mail_configuration'];

    function Mail_configurationDetailController($scope, $rootScope, $stateParams, entity, Mail_configuration) {
        var vm = this;
        vm.mail_configuration = entity;
        vm.load = function (id) {
            Mail_configuration.get({id: id}, function(result) {
                vm.mail_configuration = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:mail_configurationUpdate', function(event, result) {
            vm.mail_configuration = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
