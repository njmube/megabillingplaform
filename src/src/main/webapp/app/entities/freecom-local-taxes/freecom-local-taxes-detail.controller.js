(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_local_taxesDetailController', Freecom_local_taxesDetailController);

    Freecom_local_taxesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_local_taxes', 'Free_cfdi'];

    function Freecom_local_taxesDetailController($scope, $rootScope, $stateParams, entity, Freecom_local_taxes, Free_cfdi) {
        var vm = this;

        vm.freecom_local_taxes = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_local_taxesUpdate', function(event, result) {
            vm.freecom_local_taxes = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
