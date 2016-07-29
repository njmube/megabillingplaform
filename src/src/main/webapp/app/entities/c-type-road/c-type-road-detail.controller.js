(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_type_roadDetailController', C_type_roadDetailController);

    C_type_roadDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'C_type_road'];

    function C_type_roadDetailController($scope, $rootScope, $stateParams, entity, C_type_road) {
        var vm = this;
        vm.c_type_road = entity;
        vm.load = function (id) {
            C_type_road.get({id: id}, function(result) {
                vm.c_type_road = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:c_type_roadUpdate', function(event, result) {
            vm.c_type_road = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
