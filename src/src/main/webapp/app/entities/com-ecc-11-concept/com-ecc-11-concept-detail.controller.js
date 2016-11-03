(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_ecc_11_conceptDetailController', Com_ecc_11_conceptDetailController);

    Com_ecc_11_conceptDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_ecc_11_concept', 'Com_ecc_11', 'Com_product_key', 'C_tar'];

    function Com_ecc_11_conceptDetailController($scope, $rootScope, $stateParams, entity, Com_ecc_11_concept, Com_ecc_11, Com_product_key, C_tar) {
        var vm = this;
        vm.com_ecc_11_concept = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_ecc_11_conceptUpdate', function(event, result) {
            vm.com_ecc_11_concept = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
