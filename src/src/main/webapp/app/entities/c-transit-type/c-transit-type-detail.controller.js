(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_transit_typeDetailController', C_transit_typeDetailController);

    C_transit_typeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'C_transit_type'];

    function C_transit_typeDetailController($scope, $rootScope, $stateParams, entity, C_transit_type) {
        var vm = this;
        vm.c_transit_type = entity;
        vm.load = function (id) {
            C_transit_type.get({id: id}, function(result) {
                vm.c_transit_type = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:c_transit_typeUpdate', function(event, result) {
            vm.c_transit_type = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
