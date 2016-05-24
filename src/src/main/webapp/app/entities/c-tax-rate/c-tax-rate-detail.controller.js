(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_tax_rateDetailController', C_tax_rateDetailController);

    C_tax_rateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'C_tax_rate', 'Tax_concept'];

    function C_tax_rateDetailController($scope, $rootScope, $stateParams, entity, C_tax_rate, Tax_concept) {
        var vm = this;
        vm.c_tax_rate = entity;
        vm.load = function (id) {
            C_tax_rate.get({id: id}, function(result) {
                vm.c_tax_rate = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:c_tax_rateUpdate', function(event, result) {
            vm.c_tax_rate = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
