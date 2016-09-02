(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Tax_addressDetailController', Tax_addressDetailController);

    Tax_addressDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Tax_address', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code'];

    function Tax_addressDetailController($scope, $rootScope, $stateParams, entity, Tax_address, C_country, C_state, C_municipality, C_colony, C_zip_code) {
        var vm = this;

        vm.tax_address = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:tax_addressUpdate', function(event, result) {
            vm.tax_address = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
