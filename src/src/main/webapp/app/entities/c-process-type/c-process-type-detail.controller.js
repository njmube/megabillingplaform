(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_process_typeDetailController', C_process_typeDetailController);

    C_process_typeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'C_process_type'];

    function C_process_typeDetailController($scope, $rootScope, $stateParams, entity, C_process_type) {
        var vm = this;
        vm.c_process_type = entity;
        vm.load = function (id) {
            C_process_type.get({id: id}, function(result) {
                vm.c_process_type = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:c_process_typeUpdate', function(event, result) {
            vm.c_process_type = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
