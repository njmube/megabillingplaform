(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_commodityDetailController', Com_commodityDetailController);

    Com_commodityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_commodity', 'Com_foreign_trade', 'Com_tariff_fraction', 'Com_custom_unit'];

    function Com_commodityDetailController($scope, $rootScope, $stateParams, entity, Com_commodity, Com_foreign_trade, Com_tariff_fraction, Com_custom_unit) {
        var vm = this;
        vm.com_commodity = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_commodityUpdate', function(event, result) {
            vm.com_commodity = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
