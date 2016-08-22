(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_commodityDetailController', Freecom_commodityDetailController);

    Freecom_commodityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_commodity', 'Freecom_foreign_trade', 'Freecom_tariff_fraction', 'Freecom_custom_unit', 'Freecom_specific_descriptions'];

    function Freecom_commodityDetailController($scope, $rootScope, $stateParams, entity, Freecom_commodity, Freecom_foreign_trade, Freecom_tariff_fraction, Freecom_custom_unit, Freecom_specific_descriptions) {
        var vm = this;

        vm.freecom_commodity = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_commodityUpdate', function(event, result) {
            vm.freecom_commodity = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
