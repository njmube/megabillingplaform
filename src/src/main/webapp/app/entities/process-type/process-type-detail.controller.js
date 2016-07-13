(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Process_typeDetailController', Process_typeDetailController);

    Process_typeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Process_type'];

    function Process_typeDetailController($scope, $rootScope, $stateParams, entity, Process_type) {
        var vm = this;
        vm.process_type = entity;
        vm.load = function (id) {
            Process_type.get({id: id}, function(result) {
                vm.process_type = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:process_typeUpdate', function(event, result) {
            vm.process_type = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
