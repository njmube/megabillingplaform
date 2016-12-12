(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_data_operationDetailController', Freecom_data_operationDetailController);

    Freecom_data_operationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_data_operation', 'Freecom_public_notaries'];

    function Freecom_data_operationDetailController($scope, $rootScope, $stateParams, entity, Freecom_data_operation, Freecom_public_notaries) {
        var vm = this;
        vm.freecom_data_operation = entity;
        vm.load = function (id) {
            Freecom_data_operation.get({id: id}, function(result) {
                vm.freecom_data_operation = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_data_operationUpdate', function(event, result) {
            vm.freecom_data_operation = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
