(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_well_typeDetailController', C_well_typeDetailController);

    C_well_typeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'C_well_type'];

    function C_well_typeDetailController($scope, $rootScope, $stateParams, entity, C_well_type) {
        var vm = this;
        vm.c_well_type = entity;
        vm.load = function (id) {
            C_well_type.get({id: id}, function(result) {
                vm.c_well_type = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:c_well_typeUpdate', function(event, result) {
            vm.c_well_type = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
