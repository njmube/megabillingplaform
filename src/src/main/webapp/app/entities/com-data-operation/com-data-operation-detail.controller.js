(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_data_operationDetailController', Com_data_operationDetailController);

    Com_data_operationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_data_operation', 'Com_public_notaries'];

    function Com_data_operationDetailController($scope, $rootScope, $stateParams, entity, Com_data_operation, Com_public_notaries) {
        var vm = this;
        vm.com_data_operation = entity;
        vm.load = function (id) {
            Com_data_operation.get({id: id}, function(result) {
                vm.com_data_operation = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_data_operationUpdate', function(event, result) {
            vm.com_data_operation = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
