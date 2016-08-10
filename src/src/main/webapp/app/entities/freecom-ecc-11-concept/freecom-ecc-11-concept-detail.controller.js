(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_ecc11_conceptDetailController', Freecom_ecc11_conceptDetailController);

    Freecom_ecc11_conceptDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_ecc11_concept', 'Freecom_ecc11', 'Freecom_product_key', 'C_tar'];

    function Freecom_ecc11_conceptDetailController($scope, $rootScope, $stateParams, entity, Freecom_ecc11_concept, Freecom_ecc11, Freecom_product_key, C_tar) {
        var vm = this;

        vm.freecom_ecc11_concept = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_ecc11_conceptUpdate', function(event, result) {
            vm.freecom_ecc11_concept = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
