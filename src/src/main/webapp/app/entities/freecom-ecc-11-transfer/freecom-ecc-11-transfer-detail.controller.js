(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_ecc11_transferDetailController', Freecom_ecc11_transferDetailController);

    Freecom_ecc11_transferDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_ecc11_transfer', 'Freecom_ecc11_concept'];

    function Freecom_ecc11_transferDetailController($scope, $rootScope, $stateParams, entity, Freecom_ecc11_transfer, Freecom_ecc11_concept) {
        var vm = this;

        vm.freecom_ecc11_transfer = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_ecc11_transferUpdate', function(event, result) {
            vm.freecom_ecc11_transfer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
