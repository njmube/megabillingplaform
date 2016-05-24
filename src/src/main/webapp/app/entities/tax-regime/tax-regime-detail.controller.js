(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Tax_regimeDetailController', Tax_regimeDetailController);

    Tax_regimeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Tax_regime'];

    function Tax_regimeDetailController($scope, $rootScope, $stateParams, entity, Tax_regime) {
        var vm = this;
        vm.tax_regime = entity;
        vm.load = function (id) {
            Tax_regime.get({id: id}, function(result) {
                vm.tax_regime = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:tax_regimeUpdate', function(event, result) {
            vm.tax_regime = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
