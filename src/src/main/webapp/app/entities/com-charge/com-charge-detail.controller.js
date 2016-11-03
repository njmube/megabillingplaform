(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_chargeDetailController', Com_chargeDetailController);

    Com_chargeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_charge', 'Com_airline'];

    function Com_chargeDetailController($scope, $rootScope, $stateParams, entity, Com_charge, Com_airline) {
        var vm = this;
        vm.com_charge = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_chargeUpdate', function(event, result) {
            vm.com_charge = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
