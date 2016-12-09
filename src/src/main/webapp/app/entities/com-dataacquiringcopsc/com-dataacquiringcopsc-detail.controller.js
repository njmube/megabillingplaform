(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_dataacquiringcopscDetailController', Com_dataacquiringcopscDetailController);

    Com_dataacquiringcopscDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_dataacquiringcopsc', 'Com_acquiring_data'];

    function Com_dataacquiringcopscDetailController($scope, $rootScope, $stateParams, entity, Com_dataacquiringcopsc, Com_acquiring_data) {
        var vm = this;
        vm.com_dataacquiringcopsc = entity;
        vm.load = function (id) {
            Com_dataacquiringcopsc.get({id: id}, function(result) {
                vm.com_dataacquiringcopsc = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_dataacquiringcopscUpdate', function(event, result) {
            vm.com_dataacquiringcopsc = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
