(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_tariff_fractionDetailController', Freecom_tariff_fractionDetailController);

    Freecom_tariff_fractionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_tariff_fraction'];

    function Freecom_tariff_fractionDetailController($scope, $rootScope, $stateParams, entity, Freecom_tariff_fraction) {
        var vm = this;

        vm.freecom_tariff_fraction = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_tariff_fractionUpdate', function(event, result) {
            vm.freecom_tariff_fraction = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
