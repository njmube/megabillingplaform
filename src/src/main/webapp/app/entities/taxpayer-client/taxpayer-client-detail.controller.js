(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_clientDetailController', Taxpayer_clientDetailController);

    Taxpayer_clientDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Taxpayer_client', 'Client_address', 'Taxpayer_account'];

    function Taxpayer_clientDetailController($scope, $rootScope, $stateParams, entity, Taxpayer_client, Client_address, Taxpayer_account) {
        var vm = this;

        vm.taxpayer_client = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:taxpayer_clientUpdate', function(event, result) {
            vm.taxpayer_client = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
