(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Tax_address_requestDetailController', Tax_address_requestDetailController);

    Tax_address_requestDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Tax_address_request', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code'];

    function Tax_address_requestDetailController($scope, $rootScope, $stateParams, entity, Tax_address_request, C_country, C_state, C_municipality, C_colony, C_zip_code) {
        var vm = this;

        vm.tax_address_request = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:tax_address_requestUpdate', function(event, result) {
            vm.tax_address_request = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
