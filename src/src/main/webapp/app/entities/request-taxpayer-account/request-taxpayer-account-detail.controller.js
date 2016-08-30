(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Request_taxpayer_accountDetailController', Request_taxpayer_accountDetailController);

    Request_taxpayer_accountDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Request_taxpayer_account', 'Request_state', 'Tax_address_request', 'User'];

    function Request_taxpayer_accountDetailController($scope, $rootScope, $stateParams, entity, Request_taxpayer_account, Request_state, Tax_address_request, User) {
        var vm = this;

        vm.request_taxpayer_account = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:request_taxpayer_accountUpdate', function(event, result) {
            vm.request_taxpayer_account = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
