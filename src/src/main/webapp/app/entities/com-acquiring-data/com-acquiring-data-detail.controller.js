(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_acquiring_dataDetailController', Com_acquiring_dataDetailController);

    Com_acquiring_dataDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_acquiring_data', 'Com_public_notaries'];

    function Com_acquiring_dataDetailController($scope, $rootScope, $stateParams, entity, Com_acquiring_data, Com_public_notaries) {
        var vm = this;
        vm.com_acquiring_data = entity;
        vm.load = function (id) {
            Com_acquiring_data.get({id: id}, function(result) {
                vm.com_acquiring_data = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_acquiring_dataUpdate', function(event, result) {
            vm.com_acquiring_data = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
