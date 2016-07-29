(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_retentions_transferedDetailController', Freecom_retentions_transferedDetailController);

    Freecom_retentions_transferedDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_retentions_transfered', 'Freecom_local_taxes'];

    function Freecom_retentions_transferedDetailController($scope, $rootScope, $stateParams, entity, Freecom_retentions_transfered, Freecom_local_taxes) {
        var vm = this;
        vm.freecom_retentions_transfered = entity;
        vm.load = function (id) {
            Freecom_retentions_transfered.get({id: id}, function(result) {
                vm.freecom_retentions_transfered = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_retentions_transferedUpdate', function(event, result) {
            vm.freecom_retentions_transfered = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
