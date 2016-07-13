(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('School_levelDetailController', School_levelDetailController);

    School_levelDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'School_level'];

    function School_levelDetailController($scope, $rootScope, $stateParams, entity, School_level) {
        var vm = this;
        vm.school_level = entity;
        vm.load = function (id) {
            School_level.get({id: id}, function(result) {
                vm.school_level = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:school_levelUpdate', function(event, result) {
            vm.school_level = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
