(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_classDetailController', C_classDetailController);

    C_classDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'C_class'];

    function C_classDetailController($scope, $rootScope, $stateParams, entity, C_class) {
        var vm = this;
        vm.c_class = entity;
        vm.load = function (id) {
            C_class.get({id: id}, function(result) {
                vm.c_class = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:c_classUpdate', function(event, result) {
            vm.c_class = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
