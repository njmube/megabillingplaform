(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_tariff_fractionDetailController', Com_tariff_fractionDetailController);

    Com_tariff_fractionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_tariff_fraction'];

    function Com_tariff_fractionDetailController($scope, $rootScope, $stateParams, entity, Com_tariff_fraction) {
        var vm = this;
        vm.com_tariff_fraction = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_tariff_fractionUpdate', function(event, result) {
            vm.com_tariff_fraction = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
