(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_school_levelDetailController', C_school_levelDetailController);

    C_school_levelDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'C_school_level'];

    function C_school_levelDetailController($scope, $rootScope, $stateParams, entity, C_school_level) {
        var vm = this;
        vm.c_school_level = entity;
        vm.load = function (id) {
            C_school_level.get({id: id}, function(result) {
                vm.c_school_level = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:c_school_levelUpdate', function(event, result) {
            vm.c_school_level = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
