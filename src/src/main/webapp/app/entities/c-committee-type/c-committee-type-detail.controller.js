(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_committee_typeDetailController', C_committee_typeDetailController);

    C_committee_typeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'C_committee_type'];

    function C_committee_typeDetailController($scope, $rootScope, $stateParams, entity, C_committee_type) {
        var vm = this;
        vm.c_committee_type = entity;
        vm.load = function (id) {
            C_committee_type.get({id: id}, function(result) {
                vm.c_committee_type = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:c_committee_typeUpdate', function(event, result) {
            vm.c_committee_type = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
