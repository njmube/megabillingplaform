(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_local_transferedDetailController', Freecom_local_transferedDetailController);

    Freecom_local_transferedDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_local_transfered', 'Freecom_local_taxes'];

    function Freecom_local_transferedDetailController($scope, $rootScope, $stateParams, entity, Freecom_local_transfered, Freecom_local_taxes) {
        var vm = this;

        vm.freecom_local_transfered = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_local_transferedUpdate', function(event, result) {
            vm.freecom_local_transfered = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
