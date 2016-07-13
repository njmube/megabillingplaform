(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_chargeDetailController', Freecom_chargeDetailController);

    Freecom_chargeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_charge', 'Freecom_airline'];

    function Freecom_chargeDetailController($scope, $rootScope, $stateParams, entity, Freecom_charge, Freecom_airline) {
        var vm = this;
        vm.freecom_charge = entity;
        vm.load = function (id) {
            Freecom_charge.get({id: id}, function(result) {
                vm.freecom_charge = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_chargeUpdate', function(event, result) {
            vm.freecom_charge = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
